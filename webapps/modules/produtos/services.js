'use strict';

produtosApp.factory('Produto', ['$resource',
 	function($resource){
	    return $resource(
	    	'/meubar/api/' + produtosApp.name + '/:id',
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