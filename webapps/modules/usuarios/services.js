'use strict';

usuariosApp.factory('Usuario', ['$resource',
 	function($resource){
	    return $resource(
	    	'http://localhost:8080/meubar/api/' + usuariosApp.name + '/:id',
	    	{id : '@id'},
	    	{
	      		'query': {
		      		method:'GET', 
	      			isArray:true
	      		},
	      		'get': {
		      		method:'GET', 
	      			isArray:false
	      		},
	      		'save': {
		      		method:'POST', 
	      			isArray:false
	      		},
	      		'delete': {
		      		method:'DELETE', 
	      			isArray:false
	      		},
	      		'update': {
		      		method:'PUT', 
	      			isArray:false
	      		},
	    	}
	    );
 	}
 ]);