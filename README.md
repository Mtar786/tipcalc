As a user, when I launch the application, I should see a text edit (labeled "Amount") allowing me to enter the service charge, and a pushbutton labeled "Tip". The "Tip" pushbutton should be disabled.

As a user, when I enter data into the service charge textedit, only numbers should be allowed.

As a user, when I enter data into the service charge textedit, it should read like a USD currency amount ("$15.23"); two decimal places, and prefixed by "$".

As a user, when I enter data into the service charge textedit, it should enable the "Tip" pushbutton.

As a user, when I push the "Tip" pushbutton, it should calculate the tip (15% of the value in the service charge textedit) and display that value in a Toast message. The value displayed should be displayed as USD currency ("$15.23"): two decimal places, prefixed by "$".
