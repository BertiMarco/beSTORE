from flask import Flask, render_template


app = Flask(__name__)


@app.route('/')
def main():
    lista = []
    lista.append(('1', 'nome', 'marca', 'modello'))
    lista.append(('2', 'nome2', 'marca2', 'modello'))
    return render_template('index.html', lista=lista)

if __name__ == '__main__':
    app.run(debug=True)
