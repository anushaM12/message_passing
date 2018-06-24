%% @author anush
%% @doc @todo Add description to calling.


-module(calling).

%% ====================================================================
%% API functions
%% ====================================================================
-export([start_thread/1 ,send_msg/1]).



%% ====================================================================
%% Internal functions
%% ====================================================================

start_thread(X) ->
	  register(X, spawn(calling, send_msg, [X])).

send_msg(Msg) ->
	
    receive
		{From,"send_msg"}->
			{MegaSeconds, Seconds, MicroSeconds} = erlang:now(),
			whereis(master)!{From,Msg,intromessage,MicroSeconds},
			timer:sleep(round(timer:seconds(0.1*(rand:uniform())))),
			whereis(From)!{Msg,"response",MicroSeconds},
			send_msg(Msg);
        {From, "response",Time} ->
			
			whereis(master)!{From,Msg,replymessage,Time},
			send_msg(Msg)
	
		after 1000->
			whereis(master)!{Msg}
    end.
