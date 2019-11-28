package byeduck.lunchroom.lunch.exceptions

import byeduck.lunchroom.error.exceptions.ResourceNotFoundException

class LunchProposalNotFoundException(proposalId: String) : ResourceNotFoundException("LunchProposal", proposalId)