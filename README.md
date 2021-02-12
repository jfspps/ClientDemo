# ClientDemo
A barebones example of a client (Java)

This project should be compiled and run alongside the [ServerDemo](https://github.com/jfspps/ServerDemo) app.

The pair of projects (this and [ServerDemo](https://github.com/jfspps/ServerDemo)) demonstrate TCP and UDP connections. TCP packets are sent from the client and confirmed by a response from the server. The packet does not contain information regarding an address (which address is set by the client: in this project, the address is the client's address) or port number used. UDP datagrams (effectively UDP packets) do contain address and port info and are sent from the client without confirmation or response from the server, by default.

In this project, we implement the feedback from the server (for demonstration purposes) by extracting the address and port number from the datagram received (which in this project is the client's) and send it back (to the client).

TCP connections tend to be used when data integrity and as a result of packet checking, is the slower of the two protocols. UDP connections tend to be used when datagram (packet) drops are permitted and higher transmission speeds are required. As such, UDP tends to be applied to VoIP and video streaming applications.