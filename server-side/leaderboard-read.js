function read(query, user, request) {

    query.orderByDescending('Points');

    request.execute();

}