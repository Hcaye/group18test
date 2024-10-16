package com.example.seg_project_d11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Organizer_2 extends AppCompatActivity {
    //go back and submit buttons
    Button goBack;
    Button submit;

    //User input fields
    TextView orgName, orgEmail, orgPassword, orgConfirmPassword;

    //ViewModel initialization, hold user information in static variables
    AccountsViewModel organizationViewModel;

    //String, holds the combination of all user input, used in the userInfo txt file
    String userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizer2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Initializes view model
        organizationViewModel = new ViewModelProvider(this).get(AccountsViewModel.class);

        //initializes the Back and submit buttons
        submit= findViewById(R.id.submitButton);
        goBack = findViewById(R.id.backButton_o2);

        //initializes the userInfo string
        userInfo = null;

        //Associates each textField to TextView variable
        orgName= findViewById(R.id.organizationName);
        orgEmail= findViewById(R.id.organizerEmail);
        orgPassword= findViewById(R.id.organizerPassword);
        orgConfirmPassword= findViewById(R.id.organizerConfirmPassword);

        //Sets the text on each text field to be the user's information,
        //the default is null (empty) if nothing has been entered yet
        orgName.setText(AccountsViewModel.organizationName);
        orgEmail.setText(AccountsViewModel.organizerEmail);
        orgPassword.setText(AccountsViewModel.organizerPassword);


        //On click activity for Back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Stores the data of this page into the viewModel class's static variables
                AccountsViewModel.organizationName = orgName.getText().toString().trim();
                AccountsViewModel.organizerEmail = orgEmail.getText().toString().trim();
                AccountsViewModel.organizerPassword = orgPassword.getText().toString().trim();

                //goes back to Organizer_1 activity
                Intent intent = new Intent(Organizer_2.this, Organizer_1.class);
                startActivity(intent);
            }
        });

        submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Stores the data of this page into the viewModel class's static variables
                AccountsViewModel.organizationName = orgName.getText().toString().trim();
                AccountsViewModel.organizerEmail = orgEmail.getText().toString().trim();
                AccountsViewModel.organizerPassword = orgPassword.getText().toString().trim();


                //boolean variable to make sure all user inputs are valid before proceeding to next activity
                boolean allValid = true;

                if (!UserValidator.validateEmail(AccountsViewModel.organizerEmail)){
                    orgEmail.setError("Invalid email! email must contain @ and dot.");
                    allValid = false;
                }

                if (!UserValidator.validatePassword(AccountsViewModel.organizerPassword)){
                    orgPassword.setError("Invalid password! password must contain at least one letter and one number.");
                    allValid = false;
                }
                if (allValid){
                    //Intent intent = new Intent(Organizer_2.this, MainActivity.class);
                    //This functions should be allocated to the submit button, but in the mean times I will leave it here for testing purposes
                    userInfo = "Organizer//" + AccountsViewModel.organizerName + "//" + AccountsViewModel.organizerPhone + "//" + AccountsViewModel.organizerLastName + "//" +  AccountsViewModel.organizerAddress + "//" +  AccountsViewModel.organizationName + "//" + AccountsViewModel.organizerEmail + "//" +  AccountsViewModel.organizerPassword;
                    saveUserInfo(userInfo);
                    Intent intent = new Intent(Organizer_2.this, WelcomePage.class);
                    intent.putExtra("user_name", AccountsViewModel.organizerEmail);
                    intent.putExtra("user_role", "organizer");
                    startActivity(intent);
                }else{
                    //show a message to the user about fixing the errors
                    Toast.makeText(Organizer_2.this, "Please correct the errors before proceeding.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //Creates text file (if not created already), and writes onto it the user information separated by "//"
    private void saveUserInfo(String registrationInformation) {
        //creates file
        File userInfo = new File(getFilesDir(),"UserInfo.txt");

        try{
            //Checks if the file has already been created
            if (userInfo.createNewFile()) {
                Log.d("File creation", "File created: " + userInfo.getName());
            } else {
                Log.d("File creation", "File already exists, name: " + userInfo.getName());
            }

            //Initialized fileWriter and buffered writer, which serves to write the user info into the text file
            FileWriter fileWriter= new FileWriter(userInfo, true);
            BufferedWriter bufferedWriter= new BufferedWriter(fileWriter);
            bufferedWriter.write(registrationInformation);
            //jumps to next line on file
            bufferedWriter.newLine();
            //closes writer
            bufferedWriter.close();

            //Tests file contents, prints everything on file currently
            try {
                Scanner myReader = new Scanner(userInfo);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    Log.d("File contents", data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                Log.d("FILE ERROR","An error occurred.");
            }

        }catch(Exception e){
            Log.d("File Creation", "Error creating file"+ e);
        }
    }
}