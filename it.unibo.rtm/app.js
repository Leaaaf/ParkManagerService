var express = require('express');
var path = require('path');

var app = express();
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);

// COAP 
const coap = require('coap')
const coapServer = coap.createServer()

app.get("/", function (req, res) {
  res.send("hello world");
});

io.on("connection", (socket) => {
  console.log("a user connected");
  console.log("sokcet connection: " + socket.connected);
  console.log("sokcet client: " + socket.client.request.headers.origin);

  socket.emit("fanstate", "ON");
  socket.emit("trolleystate", "IDLE");
  socket.emit("antifiremode", "AUTO");
  socket.on("fanResponse", (payload) => {
    console.log("receive greeting from client: ", payload);
  });
}); 

//module.exports = app
server.listen(8000)

// coapServer.listen(() => {
//   console.log("RTM | Coap Server listening...")
//   const req = coap.request('coap://localhost:8001/ctxthermometer/thermometeractor')
//   req.on('response', (res) => {
//     res.pipe(process.stdout)
//     res.on('end', () => {
//       process.exit(0)
//     })
//   })

//   req.end();
// })



/*
{"resource": "thermometer","coapUrl": "coap://localhost:8001/ctxthermometer/thermometeractor","fwport": "0","fwwsurl":"ws://localhost:8000/manager/thermometer/","fwprotocol": "ws", "proxytype": "coroutined"}
{"resource": "fan","coapUrl": "coap://localhost:8002/ctxfan/fanactor","fwport": "0","fwwsurl":"ws://localhost:8000/manager/fan/","fwprotocol": "ws", "proxytype": "coroutined"}
{"resource": "sonar","coapUrl": "coap://localhost:8003/ctxsonar/sonaractor","fwport": "0","fwwsurl":"ws://localhost:8000/manager/sonar/","fwprotocol": "ws", "proxytype": "coroutined"}
{"resource": "weightsensor","coapUrl": "coap://localhost:8004/ctxweightsensor/weightsensoractor","fwport": "0","fwwsurl":"ws://localhost:8000/manager/weightsensor/","fwprotocol": "ws", "proxytype": "coroutined"}
{"resource": "parkingmanager","coapUrl": "coap://localhost:8010/ctxcarparking/parkingmanagerservice","fwport": "0","fwwsurl":"ws://localhost:8000/manager/carparking/","fwprotocol": "ws", "proxytype": "coroutined"}
{"resource": "trolley","coapUrl": "coap://localhost:8010/ctxcarparking/trolley","fwport": "0","fwwsurl":"ws://localhost:8000/manager/trolley/","fwprotocol": "ws", "proxytype": "coroutined"} 
*/