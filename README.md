# Kalaha
Java implementation of the Kalaha(Mankala) game

To intstall and run:
1) open the zip file, go to the Kalaha directory and run 'mvn clean install'
2) to run just use: java -jar .\target\Kalaha-0.0.1-SNAPSHOT.jar

to play, open your browser in : http://localhost:8080/index.html

The code is based on spring-boot and the MVC model.

The controller, KalahaController call the the KalahaService methods by using the IKalahaService (interface). The service holds a KalahaBoard (which in turn holds players, and pits) and a KalahaEngine which is stateless.

This means that with minimal effort this can be expanded to play with multi boards (we could hold for example as many engine we would like and each board will be sent to an available engine when needed)

Once the Engine finish rearrange the board according to the game's rules, the service ask the board for its pits and from them arrange the state to be sent back to the view. The state is represented by a list of integer values which represents the stones number in each pit.

The view itself is a very simple html with css (so the buttons which represents the pits will be shown nicely) and java scripts which calls the RESTful api.

It is possible to start a new game at any time by pressing the "start new game" on the top.
messages about the game state (which player's turn it is now, who is the winner etc...) are available below the board.

Some unit tests which can be found in the tests directory.
