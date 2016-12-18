package nyc.c4q.helenchan.nekoclone;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import nyc.c4q.helenchan.nekoclone.model.Chinchilla;
import nyc.c4q.helenchan.nekoclone.model.DatabaseHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by helenchan on 12/16/16.
 */

public class ChangeNameDialogFragment extends DialogFragment {
    public static final String CHINCHILLA_ID_KEY = "chinchilla_id_key";

    EditText enterName;
    String newName;
    @Inject
    public DatabaseHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ((MyApplication) getActivity().getApplication()).getComponent().inject(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_namechange, null);
        final long chinchillaID = getArguments().getLong(CHINCHILLA_ID_KEY);
        enterName = (EditText) view.findViewById(R.id.editname_et);
        builder.setView(view)
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db = dbHelper.getWritableDatabase();
                        newName = enterName.getText().toString();
                        Chinchilla chin = cupboard().withDatabase(db).get(Chinchilla.class,chinchillaID);
                        chin.setName(newName);
                        cupboard().withDatabase(db).put(chin);
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
