Assignment 3, SDJ2

(MVVM, Observer, RMI)

The assignment:

You must redesign and reimplement assignment 2 as an RMI solution.

Requirements

• The application must use RMI connecting client and server

• The client must be able to 1) send messages, 2) receive messages broadcasted to all clients and 3) request an information from the server, e.g. number of connected chatters, list of connected chatters or similar. This information will not be broadcasted to other clients

• You must use MVVM with at least two windows. Some ideas:

o Actual chat window

o Set username / alias window

o List of friends window

o Login window

• You must use the Observer design pattern as part of the solution.

• You must use either Singleton or Multiton as a log to the server console and to file(s). It should always be possible to find all the communication for an entire day –text, IP address, date and time

• It is required to make a class diagram for the final solution (in Astah). In the diagram you must be able to identify the MVVM, the Observer pattern and the design of the RMI related classes

Format

It is ok to work in groups, but each of you have to hand in a single zip-file with

• Class diagram (where the different patterns and the RMI classes are clearly identified)

• Source code for all Java classes

• Related resources like fxml files and, if used, external jar files
