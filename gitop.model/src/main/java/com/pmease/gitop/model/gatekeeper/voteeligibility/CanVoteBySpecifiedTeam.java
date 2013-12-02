package com.pmease.gitop.model.gatekeeper.voteeligibility;

import com.pmease.gitop.model.PullRequest;
import com.pmease.gitop.model.Team;
import com.pmease.gitop.model.User;

@SuppressWarnings("serial")
public class CanVoteBySpecifiedTeam implements VoteEligibility {

    private final Long teamId;
    
    public CanVoteBySpecifiedTeam(Team team) {
        this.teamId = team.getId();
    }
    
    @Override
    public boolean canVote(User user, PullRequest request) {
        for (Team team: user.getTeams()) {
            if (team.getId().equals(teamId))
                return true;
        }
        return false;
    }

}