package Models;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import AppBD.BD;
import Helper.Base64Custom;

public class Placa implements Comparable{

    private String id;
    private String placa;
    private Usuario usuario;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void salvar() {
        DatabaseReference databaseReference = BD.getFirebase();
        databaseReference.child("placas").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapPlaca = new HashMap<>();
        hashMapPlaca.put("id", getId());
        hashMapPlaca.put("placa", getPlaca());
        hashMapPlaca.put("usuario",getUsuario());
        return hashMapPlaca;
    }

    @Override
    public String toString() {
        return "Placa: "+getPlaca();
    }


    @Override
    public int compareTo(@NonNull Object o) {
        Placa outraPlaca = (Placa) o;
        if(Integer.parseInt(getId()) < Integer.parseInt(outraPlaca.getId())){
            return -1;
        }if(Integer.parseInt(getId()) > Integer.parseInt(outraPlaca.getId())){
            return 1;
        }
        return 0;
    }
}
