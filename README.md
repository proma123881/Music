##choices and decisions you made
 ***DB used: Embedded Mongodb
 ***I have implemeted 4 separate endpoints for GET Artist operation:
 ** Get Artist:
 
 * Get All Artists : /artists
 * Get All Artists sorted by name :/artists/sort?sort=
 * Get All Artist filter by name: /artists/filter?search
 * Get All Artist paginated:http: /artists/page
 
 ***I have implemented 3 seperate endpoints for GET Album for Artist
 **Get Album:
 
 * Get all Albums for a Artist: /artists/{artistId}/albums
 * Get all Albums for a Artist sorted by release year and albumName: /artists/{artistId}/albums/sort?sort=
 * Get all Albums for a Artist filter by Genre: /artists/{artistId}/albums/filter?search
 

## parts you found easy and difficult

**Easy
* Overall it was easy. 
**Difficult
* Figuring out how Discogs API work took a bit of time but the documentation was
   clear and crisp which helped a lot

## parts you skipped and the ones that you implemented extra
** Extra implementation
 * Implemented Docker 
 * Implemented swagger but not able to complete
 
** Parts Skipped

  *Test case for DisCogsAPIService & Client

