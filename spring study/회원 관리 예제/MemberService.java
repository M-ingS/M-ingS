package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
//    private final MemoryMemberRepository memberRepository = new MemoryMemberRepository();
//    Test에 있는 memberRepository와 다른 인스턴스 객체이기 때문에 수정이 필요함.
    private final MemoryMemberRepository memberRepository;

    public MemberService(MemoryMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //memberRepository를 직접 생성하는 것이 아니라 외부에서 넣어줄 수 있도록 변경

    /**
     * 회원가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원X
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
             });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findByid(memberId);
    }
}
