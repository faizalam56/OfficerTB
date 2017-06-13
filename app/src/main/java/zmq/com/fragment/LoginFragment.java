package zmq.com.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.andexert.library.RippleView;

import zmq.com.floatlable.R;

/**
 * Created by zmq162 on 28/12/15.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, RippleView.OnRippleCompleteListener {


    private Toolbar toolbar;
    private EditText inputName, inputPassword; //inputEmail,
    private TextInputLayout inputLayoutName, inputLayoutPassword;//inputLayoutEmail,
    private Button btnSignUp;
    RippleView rippleView;
    CoordinatorLayout coordinatorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.login_frag, container, false);
        return view;
    }

    LoginCallbacks comm;




    public interface LoginCallbacks{

    //    public void loginClicked(String userId, String email, String password);
        public void loginClicked(String userId, String password);
    }

    public void setLoginCallbacks(LoginCallbacks comm) {
        this.comm = comm;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("TB Officer");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        rippleView = (RippleView) getActivity().findViewById(R.id.more);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.mCordinatoLayout);




        inputLayoutName = (TextInputLayout) getActivity().findViewById(R.id.input_layout_name);
//        inputLayoutEmail = (TextInputLayout) getActivity().findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) getActivity().findViewById(R.id.input_layout_password);
        inputName = (EditText) getActivity().findViewById(R.id.input_name);
       // inputEmail = (EditText) getActivity().findViewById(R.id.input_email);
        inputPassword = (EditText) getActivity().findViewById(R.id.input_password);
        btnSignUp = (Button) getActivity().findViewById(R.id.btn_signup);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
      //  inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        btnSignUp.setOnClickListener(this);
        rippleView.setOnRippleCompleteListener(this);


//        inputName.setText("DMCLT01");
//        inputPassword.setText("z5001");

    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
//                case R.id.input_email:
//                    validateEmail();
//                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

//    private boolean validateEmail() {
//        String email = inputEmail.getText().toString().trim();
//
//        if (email.isEmpty() || !isValidEmail(email)) {
//            inputLayoutEmail.setError(getString(R.string.err_msg_email));
//            requestFocus(inputEmail);
//            return false;
//        } else {
//            inputLayoutEmail.setErrorEnabled(false);
//        }
//
//        return true;
//    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    /*
    * rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

    @Override
    public void onComplete(RippleView rippleView) {
        Log.d("Sample", "Ripple completed");
    }

});*/

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_signup:
            //    comm.loginClicked(inputName.getText().toString(),inputEmail.getText().toString(), inputPassword.getText().toString());
                comm.loginClicked(inputName.getText().toString(), inputPassword.getText().toString());
                break;

            default:
                break;
        }
    }

    @Override
    public void onComplete(RippleView rippleView) {

   //     comm.loginClicked(inputName.getText().toString(), inputPassword.getText().toString());

//        Snackbar snackbar = Snackbar.make(coordinatorLayout, "SHOW MESSAGE", Snackbar.LENGTH_INDEFINITE);
//        // Changing message text color
//        snackbar.setActionTextColor(Color.RED);
//        // Changing action button text color
//        View sbView = snackbar.getView();
//        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(Color.YELLOW);
//        snackbar.setAction("Action Me", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getActivity(),"ZMQ",Toast.LENGTH_LONG).show();
//            }
//        });
//        snackbar.show();
    }
}

// http://localhost/Tb_platform_webtool/DMC_ControllerForAndroid/submitApprovalOfPatientAddressModel?Pid=&verifiedPatientStatus=&notVerifiedPatientStatus=&verificationDate=