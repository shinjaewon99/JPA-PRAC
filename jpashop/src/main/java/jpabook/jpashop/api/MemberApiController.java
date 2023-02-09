package jpabook.jpashop.api;

import jpabook.jpashop.domain.entity.Member;
import jpabook.jpashop.domain.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

//@Controller + @ResponseBody (값을 Json,Xml로 보낸다) = @RestController
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 엔티티의 정보들이 외부에 다 노출된다.
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        // 엔티티 -> DTO 변환
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);

        /*
       for (Member findMember : findMembers) {
            MemberDto memberDto = new MemberDto(findMember.getName());
            collect.add(memberDto);
        }
        return new Result(collect);
        */
    }

    // 제네릭 타입으로 한번 감싼다. = 유연성이 올라간다.
    @Data
    @AllArgsConstructor
    static class Result<T> {
        public int count;
        private T data;

    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    // 장점아닌 장점 : CreateMemberRequest 클래스를 만들지 않아도된다. (엔티티를 꺼내서 직접사용하면안됨)
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }


    /**
     * 장점: 엔티티 클래스의 필드명이 바뀌게 되면 컴파일 오류가 발생한다
     * API 스펙을 파악하기 쉽고, 유지보수하기 쉽다.
     */

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        // memberService에서 findOne() 할때는  업데이트가 된 id값이 반환
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());


    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor // 생성자 자동생성
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

}
