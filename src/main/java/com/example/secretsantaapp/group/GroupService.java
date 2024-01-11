package com.example.secretsantaapp.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    //Add searching for user Emails
    //Add adding user to group
    //Work on statuses of groups
    //Create additional service for working with emails
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Optional<Group> getGroupById(String id) {
        return groupRepository.findById(id);
    }

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group updateGroup(String id, Group updatedGroup) {
        Optional<Group> existingGroup = groupRepository.findById(id);

        if (existingGroup.isPresent()) {
            Group group = existingGroup.get();
            group.setName(updatedGroup.getName());
            group.setEmails(updatedGroup.getEmails());
            group.setIsStarted(updatedGroup.getIsStarted());
            group.setIsFinished(updatedGroup.getIsFinished());

            return groupRepository.save(group);
        } else {
            return null;
        }
    }

    public void deleteGroup(String id) {
        groupRepository.deleteById(id);
    }

}
