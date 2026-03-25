# ImaginBlast
CS-335 Capstone Project - Simmons University

Be sure you're using:

[javafx-sdk-25.0.2+](https://jdk.java.net/javafx25/)

[jdk-25.0.2+10](https://www.oracle.com/java/technologies/javase/25-0-2-relnotes.html)

Your run configuration VM arguments should look like:

```bash
--module-path "PATH/javafx-sdk-25.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.media --enable-native-access=javafx.graphics --add-exports javafx.base/com.sun.javafx=ALL-UNNAMED
```

How to Use ImaginBlastMain.launch

Run -> Run Configurations
Click Browse next to the "Name" field
Navigate to the .launch file in their project
Select it and click Open
Click Apply -> Run

The .launch file stores relative paths if possible.
The JavaFX path is absolute (D:/Program Files/JavaFX/...), so you will need to either:
Edit the .launch file (right-click > Run As > Run Configurations > Arguments tab)
to update the --module-path to their own JavaFX location

Future: Store JavaFX in a common relative location like lib/javafx-sdk-25.0.2 inside the project,
so the path is project-relative and works for everyone.