Java-Python Interop
===================

1. Install Pyro4
```$pip install pyro4```
2. Start Pyro4 name server
```$pyro4-ns```
You should get something like this
```Not starting broadcast server for localhost.
NS running on localhost:9090 (127.0.0.1)
Warning: HMAC key not set. Anyone can connect to this server!
URI = PYRO:Pyro.NameServer@localhost:9090```
3. Write some Python code to perform remote invocations on using Pyro4.
```python
import Pyro4

@Pyro4.expose
class GreetingMaker(object):
    def get_fortune(self, name):
        return "Hello, {0}. Here is your fortune message:\n" \
               "Tomorrow's lucky number is 12345678.".format(name)

daemon = Pyro4.Daemon()                # make a Pyro daemon
ns = Pyro4.locateNS()                  # find the name server
uri = daemon.register(GreetingMaker)   # register the greeting maker as a Pyro object
ns.register("example.greeting", uri)   # register the object with a name in the name server

print("Ready.")
daemon.requestLoop()                   # start the event loop of the server to wait for calls
```
4. Execute the above code
```python greeting_server.py```
You should get the message
```Ready.```
5. Write some Java code to use Pyro. Note that this project contains a Maven pom.xml in order to include Pyro dependencies and build an uber-jar with these dependencies.
```java
import net.razorvine.pyro.*;

public class HelloPython {

	public static void main(final String[] args) throws Exception {

		final NameServerProxy ns = NameServerProxy.locateNS(null);
		final PyroProxy remoteobject = new PyroProxy(ns.lookup("example.greeting"));
		final String result = (String) remoteobject.call("get_fortune", "Marek");
		System.out.println(result);
	}
}```
6. Build the Java code
```mvn clean package```
7. Test the Java code
```mvn test```
This will run the main method. You should see the following on the console
```Hello, Marek. Here is your fortune message:
Tomorrow's lucky number is 12345678.```
