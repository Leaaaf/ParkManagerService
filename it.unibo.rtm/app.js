var express = require('express');
var path = require('path');

var app = express();
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);

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
