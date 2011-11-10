Methods for performance enhancement:

  Multiple threads -- All MYSQL statements get run on their own thread. This allows full utilization of the processor and the MYSQL connection.

  Connection pool -- Uses a pool of MYSQL connections to reduce statement setup and takedown. 

  HashTables -- Store a cached copy of the smaller databases (genres, people, booktitles, publishers) to prevent asking MYSQL for the ID each time. Takes up a lot of memory but has O(1).

  Alter Table Add Unique -- Setting the name field on the smaller databases (genres, people, booktitles, publishers) to unique prevents duplicates and speeds up inserts by creating an index.

Time Reductions:

  