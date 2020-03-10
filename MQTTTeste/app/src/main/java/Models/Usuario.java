package Models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import AppBD.BD;

public class Usuario {

    private String id;
    private String username;
    private String senha;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void salvar() {
        DatabaseReference databaseReference = BD.getFirebase();
        databaseReference.child("usuarios").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapUser = new HashMap<>();
        hashMapUser.put("id", getId());
        hashMapUser.put("username", getUsername());
        hashMapUser.put("senha",getSenha());
        return hashMapUser;
    }
}
