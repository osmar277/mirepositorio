package com.mailss.mailss;
2	
3	import android.app.Activity;
4	import android.os.Bundle;
5	
6	import java.util.Properties;
7	import javax.mail.Authenticator;
8	import javax.mail.Message;
9	import javax.mail.MessagingException;
10	import javax.mail.PasswordAuthentication;
11	import javax.mail.Session;
12	import javax.mail.Transport;
13	import javax.mail.internet.InternetAddress;
14	import javax.mail.internet.MimeMessage;
15	
16	import android.app.ProgressDialog;
17	import android.content.Context;
18	import android.os.AsyncTask;
19	import android.view.View;
20	import android.view.View.OnClickListener;
21	import android.widget.Button;
22	import android.widget.EditText;
23	import android.widget.Toast;
24	
25	
26	public class MainActivity extends Activity implements OnClickListener{
27	
28	    Session session = null;
29	    ProgressDialog pdialog = null;
30	    Context context = null;
31	    EditText reciep, sub, msg;
32	    String rec, subject, textMessage;
33	
34	    @Override
35	    protected void onCreate(Bundle savedInstanceState) {
36	        super.onCreate(savedInstanceState);
37	        setContentView(R.layout.activity_main);
38	
39	        context = this;
40	
41	        Button login = (Button) findViewById(R.id.btn_submit);
42	        reciep = (EditText) findViewById(R.id.et_to);
43	        sub = (EditText) findViewById(R.id.et_sub);
44	        msg = (EditText) findViewById(R.id.et_text);
45	
46	        login.setOnClickListener(this);
47	    }
48	
49	    @Override
50	    public void onClick(View v) {
51	        rec = reciep.getText().toString();
52	        subject = sub.getText().toString();
53	        textMessage = msg.getText().toString();
54	
55	        Properties props = new Properties();
56	        props.put("mail.smtp.host", "smtp.gmail.com");
57	        props.put("mail.smtp.socketFactory.port", "465");
58	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
59	        props.put("mail.smtp.auth", "true");
60	        props.put("mail.smtp.port", "465");
61	
62	        session = Session.getDefaultInstance(props, new Authenticator() {
63	            protected PasswordAuthentication getPasswordAuthentication() {
64	                return new PasswordAuthentication("reservaalinstante@gmail.com", "Reservaalinstante3");
65	            }
66	        });
67	
68	        pdialog = ProgressDialog.show(context, "", "Enviando Mail...", true);
69	
70	        RetreiveFeedTask task = new RetreiveFeedTask();
71	        task.execute();
72	    }
73	
74	    class RetreiveFeedTask extends AsyncTask<String, Void, String> {
75	
76	        @Override
77	        protected String doInBackground(String... params) {
78	
79	            try{
80	                Message message = new MimeMessage(session);
81	                message.setFrom(new InternetAddress("testfrom354@gmail.com"));
82	                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
83	                message.setSubject(subject);
84	                message.setContent(textMessage, "text/html; charset=utf-8");
85	                Transport.send(message);
86	            } catch(MessagingException e) {
87	                e.printStackTrace();
88	            } catch(Exception e) {
89	                e.printStackTrace();
90	            }
91	            return null;
92	        }
93	
94	        @Override
95	        protected void onPostExecute(String result) {
96	            pdialog.dismiss();
97	            reciep.setText("");
98	            msg.setText("");
99	            sub.setText("");
100	            Toast.makeText(getApplicationContext(), "Mensaje Enviado, Gracias por Utilizar Mailss", Toast.LENGTH_LONG).show();
101	        }
102	    }
103	}
