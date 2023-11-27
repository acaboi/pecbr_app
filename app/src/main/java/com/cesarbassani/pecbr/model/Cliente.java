package com.cesarbassani.pecbr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.cesarbassani.pecbr.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cliente implements Serializable, Parcelable {

    private String id;
    private String nomeCliente;
//    private List<Propriedade> propriedades;

    public Cliente() {
    }

    public Cliente(Parcel in) {
        this.id = in.readString();
        this.nomeCliente = in.readString();
//        this.propriedades = in.readArrayList(Propriedade.class.getClassLoader());
    }

    public void salvar() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("clientes");
        String id = databaseReference.push().getKey();
        databaseReference.child(id).setValue(this);
    }

    public void atualizar() {
        DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();

        DatabaseReference clientesRef = database.child("clientes")
                .child(getId());

        Map<String, Object> valoresCliente = converterParaMap();

        clientesRef.updateChildren(valoresCliente);
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    @Exclude
    private Map<String, Object> converterParaMap() {
        HashMap<String, Object> clienteMap = new HashMap<>();
        clienteMap.put("nomeCliente", getnomeCliente());
//        clienteMap.put("propriedades", getPropriedades());

        return clienteMap;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getnomeCliente() {
        return nomeCliente;
    }

    public void setnomeCliente(String getnomeCliente) {
        this.nomeCliente = getnomeCliente;
    }

//    public List<Propriedade> getPropriedades() {
//        return propriedades;
//    }

//    public void setPropriedades(List<Propriedade> propriedades) {
//        this.propriedades = propriedades;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nomeCliente);
//        Object[] objectsPropriedades = propriedades.toArray();
//        dest.writeArray(objectsPropriedades);
    }

    @Override
    public String toString() {
        return nomeCliente;
    }
}
