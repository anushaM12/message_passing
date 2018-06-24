%% @author anush
%% @doc @todo Add description to test.


-module(exchange).

%% ====================================================================
%% API functions
%% ====================================================================
-import(calling,[start_thread/1,send_msg/1]). 
-export([start/0,readvalues/1,listen/0]).



%% ====================================================================
%% Internal functions
%% ====================================================================


listen()->
	receive
		{From,To,intromessage,Time}->
			io:format("~w received intro message from ~w [~w]~n",[From,To,Time]),
			listen();
		{From,To,replymessage,Time}->
			io:format("~w received reply message from ~w [~w]~n",[From,To,Time]),
			listen();
        {Msg} ->
			  io:format("~n"),
			  io:format("Process ~w has received no calls for 1 second, ending..~n",[Msg]),
			  listen();
	     _ ->
			io:format("Doesn't match any pattern")
	      
	after 1500->
		io:format("~n"),
		io:format("Master has received no replies for 1.5 seconds, ending...~n")
	end.

readvalues(B) ->
	case file:read_line(B) of
	eof -> ".";
		
	{ok, Line} ->
		   StrVal=string:replace(Line, "{", " ", all),
		   StrVal1=string:replace(StrVal, "}", " ", all),
		   StrValues=string:replace(StrVal1, ",",":"),
		   io:format("~s~n",[StrValues]),
		   readvalues(B)
	end.

get_values(X,Map1) ->
	  
	  %io:fwrite("~p~n", registered()),
      MapValues=maps:get(X,Map1),
	  %io:fwrite("~p~n",[MapValues]).
	  
	  lists:foreach( fun(Y)->
						   timer:sleep(round(timer:seconds(0.1*(rand:uniform())))),
						   whereis(X) ! {Y, "send_msg"}
					end,MapValues).
	  %io:fwrite("~w~n", [registered()]).
	 

init_var(X) ->
	 start_thread(X).
	 %io:fwrite("~w~n", [registered()]).

start() -> 
   register(master, spawn(exchange,listen,[])),
   {ok,File}=file:open("calls.txt", [read]),
   {ok,B}=file:consult("calls.txt"),
   io:format("** calls to be made  **\n"),
   readvalues(File),
   file:close(File),
   Map1=maps:from_list(B),
   MapKeys=maps:keys(Map1),
   lists:foreach( fun(X)->
						  init_var(X),"." end, 
				           MapKeys),
   lists:foreach( fun(X)->
						  get_values(X,Map1), "." end, 
				           MapKeys).

  
   
