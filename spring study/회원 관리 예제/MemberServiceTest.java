package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();        //memberRepository 생성 후
        memberService = new MemberService(memberRepository);    //MemberService에 넣어줌 그럼 같은 memberRepository사용
    }                                   //이렇게 외부에서 넣어주는 방식을 DI = Dependency Injection


    @AfterEach
    public void afterEach() {
        memberRepository.clearstore();
    }
    // clear시켜줌으로써 member의 이름이 변경되어도 메소드들이 제대로 작동함

    @Test
    void 회원가입() {       //test코드는 한글로 적어도됨 (빌드될 때 포함X)
        //given         //given: 무언가가 주어짐 when: 이걸 실행했을 때 then: 결과가 이것이 나와야함
        Member member = new Member();
        member.setName("hello");
    // hello이름을 가진 멤버를 생성
        //when
        Long saveId = memberService.join(member);
    //멤버서비스 join(member)를 검증하면 save된id가 반환
        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    //저장을 한게 리포지토리에 있는 것과 일치한지 검증
    // Member의 이름이 findMember의 이름과 같은지

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
    //예외가 잘 되는지 보려고 멤버1,2 모두 같은
        // 이름을 가진 spring으로 설정

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,() -> memberService.join(member2));
    //member2를 넣으면 예외가 터져야함. IllegalStateException.class 가 터져야함
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
    //try: 예외가 터져서 실행되는 문장 catch: 정상적으로 실행

        //then


    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
