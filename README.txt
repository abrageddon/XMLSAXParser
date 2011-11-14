Methods for performance enhancement:

  Multiple threads -- All MYSQL statements get run on their own thread. This allows full utilization of the processor and the MYSQL connection.

  Connection pool -- Uses a pool of MYSQL connections to reduce statement setup and takedown. 

  HashTables -- Store a cached copy of the smaller databases (genres, people, booktitles, publishers) to prevent asking MYSQL for the ID each time. Takes up a lot of memory but has O(1).

  Alter Table Add Unique -- Setting the name field on the smaller databases (genres, people, booktitles, publishers) to unique prevents duplicates and speeds up inserts by creating an index.


Elapsed Time:

  No Enhancement: 20 Min

  Add Unique: 1 Min 

  Add Unique, Threads, Connection Pool: 1 Min

  Add Unique, HashTable: 45 Sec

  Add Unique, HashTable, Threads: 35 Sec

  Add Unique, HashTable, Threads, Connection Pool: 25 Sec