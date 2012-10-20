function read(query, user, request) {
    
    query
        .orderByDescending('id')
        .take(1);
        
    request.execute();

}