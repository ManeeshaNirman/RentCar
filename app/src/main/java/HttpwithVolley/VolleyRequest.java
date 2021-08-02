package HttpwithVolley;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyRequest {

  String emailresponse;


    public String volleyRequest(String name, String email, Activity activity,String url){
        RequestQueue queue = Volley.newRequestQueue(activity);



// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                      emailresponse=response;

                        System.out.println("respone eke code eka"+response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("volley", "onErrorResponse: "+""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map=new HashMap();
                map.put("name",name);
                map.put("email",email);
                Log.d("volley", "OK Map");
                return map;
            }


        };



        queue.add(stringRequest);

        return emailresponse;
    }

//    public String getResponse(String name, String email, Activity activity,String url){
//
//        volleyRequest(name, email,activity,url);
//        System.out.println("AWA");
//        return emailresponse;
//
//    }

}
