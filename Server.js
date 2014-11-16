var http = require('http');
var url = require('url');
var querystring = require('querystring');

var stored = {};

http.createServer(function(request, response) {
	var str = url.parse(request.url);
	var json = querystring.parse(str.query);
	key = json.authString;
	delete json.authString;
	if(str.pathname == '/post/')
	{
		console.log('print was called');
		console.log(json);
		stored[key]=json;
		response.writeHead(200);
		response.write("Thank You");
		response.end();
		stored[key].found = false;
	}
	if(str.pathname == '/get/')
	{
		if(stored.hasOwnProperty(key))
		{
			console.log('get was called');
			console.log(json);
			response.writeHead(200);
			response.write(JSON.stringify(stored[key]));
			response.end();
			stored[key] = json;
			stored[key].found = true;
		}
		else
		{
			response.writeHead(404);
			response.write("User not found");
			response.end();
		}
	}
	if(str.pathname == '/heartbeat/')
	{
		//console.log('heartbeat was called');
		//console.log(json);
		if(stored.hasOwnProperty(key))
		{
			if(stored[key].found)
			{
				response.writeHead(200);
				response.write(JSON.stringify(stored[key]));
				response.end();
				delete stored[key];
			}
			else
			{
				response.writeHead(200);
				response.write('wait');
				response.end();
			}
		}
		else
		{
			response.writeHead(404);
			response.write('User Not Found');
			response.end();
		}
	}
	else
	{
		response.writeHead(404);
		response.write("Page Not Found");
		response.end();
		return;
	}
}).listen(8888);