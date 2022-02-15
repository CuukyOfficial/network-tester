# Network-Tester
Dies ist ein Tester für die erste Programmieren Abschlussaufgabe 2022 des KIT.<br>
Im Folgenden wird die Benutzung erklärt.

### Eigene Tests schreiben
Einfach eine neue Klasse in <code>de.cuuky.networktester.tests</code> erstellen und sie <code>de.cuuky.networktester.tests.Test</code> extenden lassen. Damit könnt ihr denn die
<code>test()</code> Methode voll mit Tests füllen und z.B. Ausgaben oder Zustände mit der Methode <code>verify(String name, T test1, T test2)</code> überprüfen.<br>
Ein Beispiel ist bereits gegeben; <code>de.cuuky.networktester.tests.DefaultInteractionTest</code><br>
Danach (offensichtlich) die neuen Changes auf GitHub pushen, damit die anderen die Tests auch machen können.

### Den Tester aufsetzen
Falls auf GitHub releases vorhanden sind, könnt ihr die einfach runterladen und zu "Den Tester nutzen" springen.<br>
1. Die <code>start.bat</code> herunterladen und in einen Ordner schmeißen.<br>
2. Die GitHub Repository in IntelliJ clonen und das Programm als Jar in den oben genannten Ordner mit dem Namen "NetworkTester.jar" exportieren.<br>

### Den Tester nutzen
Euer fertiges Programm (als Jar) muss einfach in dem Ordner als <code>Test.jar</code> legen, wo auch die <code>start.bat</code> auch ist. Dann einfach<br>
die .bat starte und fertig. Der Pfad der Jar ist aber auch per Startargumente änderbar.<br>
Nachdem der NetworkTester fertig ist, könnt ihr einfach in IntelliJ euer eigenes Programm neu exportieren und enter drücken, was die Tests neu starten sollte. 
 
<br>
<br>
Happy Testing! 42
