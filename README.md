
# Chat Application with RMI

## Overview
This chat application is a reimplementation of Assignment 2, designed as an RMI solution. It uses the MVVM (Model-View-ViewModel) pattern, the Observer design pattern, and Singleton for logging purposes. The application consists of two main windows: the chat window and the set username/alias window.

## Requirements
- The application must use RMI to connect the client and server.
- The client must be able to send messages, receive broadcasted messages, and request information from the server.
- The server will not broadcast the requested information to other clients.
- The application must use MVVM with at least two windows: the chat window and the set username/alias window.
- The Observer design pattern must be used as part of the solution.
- Singleton or Multiton must be used as a log to the server console and to file(s).
- A class diagram must be provided, clearly identifying the MVVM, the Observer pattern, and the RMI-related classes.


## Source Code
The source code for all Java classes is included in the `src` folder.

## Related Resources
- FXML files for the chat window and the set username/alias window are located in the `resources` folder.
- If any external jar files are used, they are included in the `lib` folder.

## Getting Started
To run the chat application, follow these steps:

1. Unzip the provided `.zip` file.
2. Open the project in your favorite Java IDE.
3. Run the `ChatServer` and `ChatClient` classes.
4. Enjoy chatting with your friends!

## Contributors
- Maciej Matuszewski (xvvcs)
- Jakub Abramek (Richardzik)
- Mateusz Samborski (M0dlyn)

## License
This project is licensed under the MIT License - see the `LICENSE` file for details.