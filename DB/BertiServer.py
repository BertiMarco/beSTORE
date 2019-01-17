from flask import Flask, request
import psycopg2
from datetime import datetime


app = Flask(__name__)
conn = psycopg2.connect(host='localhost', database='magazzadb', user='admin',
                        password='admin')


@app.route('/select_all/')
def select_all():
    output = ''
    with conn.cursor() as cur:
        cur.execute('SELECT * FROM oggetto')
        for line in cur:
            output += '\n' + str(line)
    return output


@app.route('/check_imei/', methods=['POST'])
def check_imei():
    imei = request.form['imei']
    imei = str(imei)
    print('L\'imei è:', imei)

    with conn.cursor() as cur:
        cur.execute('SELECT nome, cognome FROM utente WHERE imei = %s', (imei,))
        print(cur.rowcount)
        if cur.rowcount >= 1:
            for line in cur:
                return 'ok,' + line[0] + ',' + line[1]
    return 'ko'


@app.route('/product_detail/', methods=['POST'])
def product_detail():
    id = request.form['id']
    print('Richiesto', id)
    try:
        int(id)
    except:
        return 'ko'

    with conn.cursor() as cur:
        cur.execute("""SELECT id, nome, marca 
                        FROM oggetto
                        WHERE id = %s AND id NOT IN
                        (SELECT id
                        FROM prestito
                        WHERE id_oggetto = %s AND restituito_il IS NULL)""", (id, id,))
        print(cur.rowcount)
        if cur.rowcount >= 1:
            for line in cur:
                return 'ok,' + str(line[0]) + ',' + line[1] + ',cacca'
    return 'ko'


@app.route('/add_to_borrow/', methods=['POST'])
def add_to_borrow():
    print(str(request.form))
    for key in request.form.keys():
        print(key, '\t->\t', request.form[key])
    evento = request.form['causale']
    imei = request.form['imei']
    date = request.form['date']
    date = datetime.fromtimestamp((int(date))/1000.0)
    print(date)
    items = request.form['itemsID'].split(",")
    items = items[:-1]

    itemBank = []
    for item in items:
        itemBank.append((
            item,
            imei,
            date,
            evento
        ))
    q = """INSERT INTO prestito (id_oggetto, imei_utente, preso_il, evento) VALUES (%s, %s, %s, %s)"""

    # #TODO-> un po' da sistemare.

    with conn.cursor() as cur:
        cur.executemany(q, itemBank)
        rows = cur.rowcount
        conn.commit()
        if rows >= 1:
            print(rows)
            return 'ok'
    return 'ok'


@app.route('/events_for_user/', methods=['POST'])
def event_for_user():
    imei = request.form['imei']
    imei = str(imei)
    print('L\'imei è:', imei)

    with conn.cursor() as cur:
        cur.execute('SELECT DISTINCT evento FROM prestito WHERE imei_utente = %s AND restituito_il IS NULL', (imei,))
        print(cur.rowcount)
        words = []
        if cur.rowcount >= 1:
            for line in cur:
                words.append(line[0])
            return 'ok,' + ','.join(words)
                #return 'ok,' + line[0] + ',' + line[1]
    return 'ko'


@app.route('/get_item_to_leave/', methods=['POST'])
def get_item_to_leave():
    id = request.form['id']
    print('Richiesto', id)
    try:
        int(id)
    except:
        return 'ko'

    with conn.cursor() as cur:
        cur.execute("""SELECT id, nome, marca, posizione 
                        FROM oggetto
                        WHERE id = %s AND id IN
                        (SELECT id
                        FROM prestito
                        WHERE id_oggetto = %s AND restituito_il IS NULL)""", (id, id,))
        print(cur.rowcount)
        if cur.rowcount >= 1:
            for line in cur:
                return 'ok,' + str(line[0]) + ',' + line[1] + ',cacca,' + line[3]
    return 'ko'


@app.route('/leave_items/', methods=['POST'])
def leave_items():
    print(str(request.form))
    for key in request.form.keys():
        print(key, '\t->\t', request.form[key])
    imei = request.form['imei']
    date = request.form['date']
    date = datetime.fromtimestamp((int(date))/1000.0)
    print(date)
    items = request.form['itemsID'].split(",")
    items = items[:-1]

    itemBank = []
    for item in items:
        itemBank.append((
            imei,
            date,
            item,
        ))
    q = """UPDATE prestito SET restituito_da = %s, restituito_il = %s WHERE id_oggetto = %s AND restituito_il IS NULL"""

    with conn.cursor() as cur:
        cur.executemany(q, itemBank)
        rows = cur.rowcount
        conn.commit()
        if rows >= 1:
            print(rows)
            return 'ok'
    return 'ok'

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
