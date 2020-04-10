package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.service.MemberService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/api/v2/members")
    public Result getMembers(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request){
        Member findMember = memberService.update(id, request.getName());
//        Member findMember = memberService.findByOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateMemberRequest{
        private String name;
    }

    @Getter
    @Setter
    static class CreateMemberResponse{
        private Long id;

        CreateMemberResponse(Long id){
            this.id = id;
        }
    }
}
