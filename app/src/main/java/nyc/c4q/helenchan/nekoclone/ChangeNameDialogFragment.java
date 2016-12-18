package nyc.c4q.helenchan.nekoclone;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import nyc.c4q.helenchan.nekoclone.model.Chinchilla;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by helenchan on 12/16/16.
 */

public class ChangeNameDialogFragment extends DialogFragment {
    EditText enterName = (EditText) getActivity().findViewById(R.id.editname_et);
    String newName;
    private SQLiteDatabase db;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_namechange,null);
        builder.setView(view)
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        newName = enterName.getText().toString();
                        ContentValues values = new ContentValues();
                        values.put("name", newName);
                        cupboard().withDatabase(db).update(Chinchilla.class, values);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }


}
