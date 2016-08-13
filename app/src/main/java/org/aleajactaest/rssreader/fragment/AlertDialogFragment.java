package org.aleajactaest.rssreader.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import org.aleajactaest.rssreader.R;

/**
 * @author Jean-Francois Yoteau
 */
public class AlertDialogFragment extends DialogFragment {

    private static final String TITLE_KEY = "title";

    private static final String MESSAGE_KEY = "message";

    public static AlertDialogFragment newInstance(int titleResId, String message) {
        final Bundle args = new Bundle();
        args.putInt(TITLE_KEY, titleResId);
        args.putString(MESSAGE_KEY, message);

        final AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();

        final int titleResId = args.getInt(TITLE_KEY, 0);
        final String message = args.getString(MESSAGE_KEY, "");

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(titleResId)
                .setMessage(message)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                )
                .create();
    }

}
