package byeduck.lunchroom.lunch.exceptions

class InvalidProposalException(proposalId: String) : IllegalArgumentException("Invalid proposal: $proposalId")