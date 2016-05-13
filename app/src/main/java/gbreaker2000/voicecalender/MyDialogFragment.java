package gbreaker2000.voicecalender;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavi on 4/25/2016.
 */
public class MyDialogFragment extends DialogFragment {

    long time;
    File file;
    String foundIndexText = "0";

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static MyDialogFragment newInstance(long num) {
        MyDialogFragment f = new MyDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putLong("num", num);
        f.setArguments(args);

        return f;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        time = getArguments().getLong("num");
        final int foundIndex = Appointment.AptDisSearch(time);
        foundIndexText = "" + foundIndex;


        FileIO fileIO = new FileIO();
        final List<Appointment> aptFile = new ArrayList<>();
        aptFile.clear();
        aptFile.addAll(fileIO.FileInput());

        String msg = Appointment.aptStringBuilder(aptFile.get(foundIndex));



        // We build the dialog
        // getActivity() returns the Activity this Fragment is associated with
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());

        // Set the title for the Dialog
        theDialog.setTitle("Appointment Info");

        // Set the message
        theDialog.setMessage(msg);

        // Add text for a positive button
        theDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                boolean deleted = false;

                if (aptFile.get(foundIndex).hasRecording())
                {
                    file = new File(aptFile.get(foundIndex).getFileName());
                    deleted = file.delete();
                }
                aptFile.remove(foundIndex);
                FileIO.FileOutput(aptFile);
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                String msg = "";
                if (deleted)
                {
                    msg = "Appointment and Voice Recording Deleted";
                }
                else
                {
                    msg = "Appointment Deleted";
                }

                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

            }
        });

        // Add text for a negative button
        theDialog.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent editApp = new Intent(getContext(), AppointmentEditor.class);
                editApp.putExtra("foundIndexText", foundIndexText);
                startActivity(editApp);
//                Toast.makeText(getActivity(), "Clicked Cancel", Toast.LENGTH_SHORT).show();

            }
        });

        // Returns the created dialog
        return theDialog.create();

    }
}