var goalTable = tables.getTable("CurrentGoal");
var leaderBoardTable = tables.getTable("LeaderBoard");

function insert(item, user, request) {

    goalTable
        .where({ObjectId:item.ObjectId})
        .read({
            success: function(results){
                var goal = results[0];
                var points = goal.CurrentPointsAward
                console.log("Goal award: " + points);
                item.Points = points;
                
                // Award the points to the user
                console.log("Updating leaderboard...");
                leaderBoardTable
                    .where({UserId:item.UserId})
                    .read({success: function(leaderEntries){
                          if(leaderEntries.length > 0)
                          {
                            // they have a record, update it
                            var leaderBoardUpdateSql = "UPDATE LeaderBoard SET TotalCheckins = TotalCheckins + 1, Points = Points + ? WHERE UserId = ?";
                            mssql.query(leaderBoardUpdateSql, [points, item.UserId]);
                          }
                          else
                          {
                            // need to insert a new record
                            var leaderBoardInsertSql = "INSERT INTO LeaderBoard (TotalCheckins, Points, UserId) VALUES (?, ?, ?)";
                            mssql.query(leaderBoardInsertSql, [1, points, item.UserId]);
                          }
                      }
                    });
                
                request.execute();          

                // Update the goal so that the points are decremented for the next user
                if(points >= 15)
                {
                    console.log("Decrementing goal from " + points + "...");
                    
                    var pointsUpdateSql = "UPDATE CurrentGoal SET CurrentPointsAward = CurrentPointsAward - 5 WHERE ObjectId = ?";
	                mssql.query(pointsUpdateSql, [item.ObjectId]);
                    
                    console.log("Points set to " + points + ".");
                }
                    
            }
        });
    
}



