package com.example.gerenciadordelivros.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.gerenciadordelivros.dominio.Livro;

public class DeleteDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private Livro livro;

    private OnDeleteListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!(context instanceof OnDeleteListener)){
            throw new RuntimeException("Não é um OnDeleteListener");
        }
        this.listener = (OnDeleteListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deseja excluir o livro " + livro.getTitulo() + " ?");
        builder.setPositiveButton("SIM", this);
        builder.setNegativeButton("NÃO", this);

        return builder.create();
    }

    public void setLivro (Livro livro){
        this.livro = livro;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE){
            listener.onDelete(livro);
        }
    }

    public interface OnDeleteListener{
            void onDelete(Livro livro);
    }
}
