package com.example.proyectodb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtRut, edtNombre, edtDireccionCalle, edtDireccionNumero, edtDireccionComuna, edtDireccionCiudad, edtTelefono, edtPaginaWeb;
    Button btnAgregar, btnEditar, btnEliminar, btnBuscar;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtRut = findViewById(R.id.edtRut);
        edtNombre = findViewById(R.id.edtNombre);
        edtDireccionCalle = findViewById(R.id.edtDireccion_calle);
        edtDireccionNumero = findViewById(R.id.edtDireccion_numero);
        edtDireccionComuna = findViewById(R.id.edtDireccion_comuna);
        edtDireccionCiudad = findViewById(R.id.edtDireccion_ciudad);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtPaginaWeb = findViewById(R.id.edtPagina_web);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnBuscar = findViewById(R.id.btnBuscar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://172.25.2.84/andoridDB/insertar_proveedor.php");
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarProveedor("http://172.25.2.84/andoridDB/buscar_proveedor.php?rut="+edtRut.getText()+"");
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://172.25.2.84/andoridDB/editar_proveedor.php");
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarProveedor("http://172.25.2.84/andoridDB/eliminar_proveedor.php");
            }
        });

    }

    private void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operación exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en la respuesta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("rut", edtRut.getText().toString());
                parametros.put("nombre", edtNombre.getText().toString());
                parametros.put("direccion_calle", edtDireccionCalle.getText().toString());
                parametros.put("direccion_numero", edtDireccionNumero.getText().toString());
                parametros.put("direccion_comuna", edtDireccionComuna.getText().toString());
                parametros.put("direccion_ciudad", edtDireccionCiudad.getText().toString());
                parametros.put("telefono", edtTelefono.getText().toString());
                parametros.put("pagina_web", edtPaginaWeb.getText().toString());

                return parametros;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    
    private void buscarProveedor(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        edtNombre.setText(jsonObject.getString("nombre"));
                        edtDireccionCalle.setText(jsonObject.getString("direccion_calle"));
                        edtDireccionNumero.setText(jsonObject.getString("direccion_numero"));
                        edtDireccionComuna.setText(jsonObject.getString("direccion_comuna"));
                        edtDireccionCiudad.setText(jsonObject.getString("direccion_ciudad"));
                        edtTelefono.setText(jsonObject.getString("telefono"));
                        edtPaginaWeb.setText(jsonObject.getString("pagina_web"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"NO ENCONTRADO", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private void eliminarProveedor(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "El Proveedor fue eliminado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en la respuesta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("rut", edtRut.getText().toString());
                return parametros;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void limpiarCampos() {
        edtRut.setText("");
        edtNombre.setText("");
        edtDireccionCalle.setText("");
        edtDireccionNumero.setText("");
        edtDireccionComuna.setText("");
        edtDireccionCiudad.setText("");
        edtTelefono.setText("");
        edtPaginaWeb.setText("");
    }
}
