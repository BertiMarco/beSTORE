package it.univr.mb.magazza.Database;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import it.univr.mb.magazza.Activity.LasciaActivity;
import it.univr.mb.magazza.Activity.PrendiActivity;
import it.univr.mb.magazza.Activity.PrendiLasciaFragments.CausaleFragment;
import it.univr.mb.magazza.Model.Borrow;
import it.univr.mb.magazza.Model.Item;
import it.univr.mb.magazza.Model.User;

public class DBInterface {
    private final static String TAG = "DBinterface";
    private final static String url = "http://store.believegroup.it/beSTORE/"; //TODO metti nelle impostazioni

    public void isImeiRegistered(Context context, String imei) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "check_imei.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "RISPOSTA" + response);
                        if (response.substring(0, 2).equals("ok")) {
                            String nome = response.split(",")[1];
                            String cognome = response.split(",")[2];
                            ObjectBuilder.getInstance().loginAccepted(nome, cognome, imei);
                        } else {
                            ObjectBuilder.getInstance().loginFailed();
                            Log.d(TAG, response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                        Toast.makeText(context, "Impossibile connettersi al database", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("imei", imei);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void getItem(String id, Context context) {
        int counter  = 0;

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "product_detail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "RISPOSTA PRODOTTO: " + response);
                        if (response.substring(0, 2).equals("ok")) {
                            String id = response.split(",")[1];
                            String nome = response.split(",")[2];
                            String brand = response.split(",")[3];
                            ObjectBuilder.getInstance().itemFound(id, nome, brand);
                        } else if (response.substring(0,2).equals("ko")) {
                            String itemId = response.split(",")[1];
                            String userImei = response.split(",")[2];
                            String userName = response.split(",")[3];
                            String userSurname = response.split(",")[4];
                            ObjectBuilder.getInstance().itemLent(itemId, userImei, userName, userSurname);
                        }
                        else
                            ObjectBuilder.getInstance().itemNotFound();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                        Toast.makeText(context, "Impossibile connettersi al database", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        Log.d(TAG, "RICHIESTA: ");
        queue.add(stringRequest);
        counter++;
        Log.d(TAG, "eseguita #" + counter);
    }

    public void commitBorrow(PrendiActivity activity) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        Borrow mBorrow = ObjectBuilder.getInstance().getBorrow();

        String ids;
        StringBuilder stringBuilder = new StringBuilder();
        for (Item i : mBorrow.getItems()) {
            stringBuilder.append(i.getID());
            stringBuilder.append(',');
        }
        ids = stringBuilder.toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "add_to_borrow.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.substring(0, 2).equals("ok")) ;
                        ObjectBuilder.getInstance().borrowCommitted();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                        Toast.makeText(activity, "Impossibile connettersi al database", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("imei", mBorrow.getUser().getImeiCode());
                params.put("causale", mBorrow.getDescription());
                params.put("date", "" + mBorrow.getDate().getTime());
                params.put("itemsID", ids);
                return params;
            }
        };
        Log.d(TAG, "effettuo la richiesta."); //Request: " + jsonObjectRequest.toString() + "\n\trequestBody: " + requestBody + "\n\trealRequest: "+ Arrays.toString(jsonObjectRequest.getBody()));
        queue.add(stringRequest);
        Log.d(TAG, "richiesta effettuata");
    }

    public void getItemToLeave(String rawValue, Context applicationContext) {
        RequestQueue queue = Volley.newRequestQueue(applicationContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "get_item_to_leave.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.substring(0, 2).equals("ok")) {
                            String id = response.split(",")[1];
                            String nome = response.split(",")[2];
                            String brand = response.split(",")[3];
                            String location = response.split(",")[4];
                            ObjectBuilder.getInstance().itemToLeaveFound(id, nome, brand, location);
                        } else
                            ObjectBuilder.getInstance().itemAlreadyInStore();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                        Toast.makeText(applicationContext, "Impossibile connettersi al database", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", rawValue);
                return params;
            }
        };
        queue.add(stringRequest);


    }

    public void getEvents(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"events.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        if (response.substring(0, 2).equals("ok")){
                            String[] tmp = response.split(",");
                            ArrayList<String> events = new ArrayList<>(Arrays.asList(tmp));
                            events.remove(0);
                            Log.d(TAG, "EVENTS: " + events.toString());
                            ObjectBuilder.getInstance().eventsFound(events);

                        }
                        else {
                            Log.d(TAG, "ramo else");
                            ObjectBuilder.getInstance().eventsFound(new ArrayList<>());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                        Toast.makeText(context, "Impossibile connettersi al database", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("imei", ObjectBuilder.getInstance().getCurrentUser().getImeiCode());
                return params;
            }
        };
        queue.add(stringRequest);
    }


    public void commitLeave(ArrayList<Item> itemsToLeave, LasciaActivity activity) {
        RequestQueue queue = Volley.newRequestQueue(activity);

        User user = ObjectBuilder.getInstance().getCurrentUser();

        String ids;
        StringBuilder stringBuilder = new StringBuilder();
        for (Item i : itemsToLeave) {
            stringBuilder.append(i.getID());
            stringBuilder.append(',');
        }
        ids = stringBuilder.toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "leave_items.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.substring(0, 2).equals("ok")) ;
                        ObjectBuilder.getInstance().leaveCommitted();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                        Toast.makeText(activity, "Impossibile connettersi al database", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("imei", user.getImeiCode());
                params.put("itemsID", ids);
                params.put("date", String.valueOf(System.currentTimeMillis()));
                return params;
            }
        };
        Log.d(TAG, "effettuo la richiesta."); //Request: " + jsonObjectRequest.toString() + "\n\trequestBody: " + requestBody + "\n\trealRequest: "+ Arrays.toString(jsonObjectRequest.getBody()));
        queue.add(stringRequest);
        Log.d(TAG, "richiesta effettuata");

    }

    //TODO-> inutile questa parte, non capisco come fare le query
    public void getEventsItems(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"events_items.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "RISPOSTA: " + response);
                        if (response.substring(0, 2).equals("ok")){
                            String[] tmp = response.split("\\.");
                            ArrayList<String> tuples = new ArrayList<>(Arrays.asList(tmp));
                            Log.d(TAG, "SPLITTED: " + tuples);
                            tuples.remove(0);
                            Log.d(TAG, "TUPLES: " + tuples.toString());
                            ObjectBuilder.getInstance().eventsItemsFound(tuples);

                        }
                        else {
                            Log.d(TAG, "ramo else");
                            //ObjectBuilder.getInstance().eventsFound(new ArrayList<>());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                        Toast.makeText(context, "Impossibile connettersi al database", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(stringRequest);
    }
}
