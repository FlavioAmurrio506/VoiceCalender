package gbreaker2000.voicecalender;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class AppointmentInfoDisplayFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // We build the dialog
        // getActivity() returns the Activity this Fragment is associated with
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());

        // Set the title for the Dialog
        theDialog.setTitle("Sample Dialog");

        // Set the message
        theDialog.setMessage("Hello I'm a Dialog");

        // Add text for a positive button
        theDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(getActivity(), "Clicked OK", Toast.LENGTH_SHORT).show();

            }
        });

        // Add text for a negative button
        theDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(getActivity(), "Clicked Cancel", Toast.LENGTH_SHORT).show();


            }
        });

        // Returns the created dialog
        return theDialog.create();

    }
}