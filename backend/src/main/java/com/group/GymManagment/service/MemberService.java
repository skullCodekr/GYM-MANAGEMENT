package com.group.GymManagment.service;

import com.group.GymManagment.model.Member;
import com.group.GymManagment.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // ➕ Create Member
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    // 📋 Read - Get all Members
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 🔍 Read - Get Member by ID
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    // ✏️ Update Member
    public Member updateMember(Long id, Member updatedMember) {
        Optional<Member> existingMemberOpt = memberRepository.findById(id);

        if (existingMemberOpt.isPresent()) {
            Member existingMember = existingMemberOpt.get();
            existingMember.setName(updatedMember.getName());
            existingMember.setPhone(updatedMember.getPhone());
            existingMember.setEmail(updatedMember.getEmail());
            existingMember.setAge(updatedMember.getAge());
            existingMember.setHeightCm(updatedMember.getHeightCm());
            existingMember.setWeightKg(updatedMember.getWeightKg());
            existingMember.setMembershipType(updatedMember.getMembershipType());
            existingMember.setJoinDate(updatedMember.getJoinDate());
            return memberRepository.save(existingMember);
        } else {
            return null; // Or throw custom exception later
        }
    }

    // ❌ Delete Member
    public boolean deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
