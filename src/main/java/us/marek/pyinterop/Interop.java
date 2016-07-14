package us.marek.pyinterop;

import net.razorvine.pyro.*;

public class Interop {

	public static void main(final String[] args) throws Exception {

		final NameServerProxy ns = NameServerProxy.locateNS(null);
		final PyroProxy remoteobject = new PyroProxy(ns.lookup("example.greeting"));
		final String result = (String) remoteobject.call("get_fortune", "Marek");
		System.out.println(result);
	}
}