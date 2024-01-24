package com.garden.back.member;

import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.member.dto.FindMyCurrentRegionRequest;
import com.garden.back.member.dto.UpdateMyRegionRequest;
import com.garden.back.member.region.FindAllMyAddressResponse;
import com.garden.back.member.region.FindMyCurrentRegionResponse;
import com.garden.back.member.region.MemberRegionService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberRegionController {

    private final MemberRegionService memberRegionService;

    public MemberRegionController(MemberRegionService memberRegionService) {
        this.memberRegionService = memberRegionService;
    }

    @GetMapping(
        value = "/my/regions",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindAllMyAddressResponse> findAllMyAddress(
        @CurrentUser LoginUser loginUser
    ) {
        return ResponseEntity.ok(memberRegionService.findAllMyAddresses(loginUser.memberId()));
    }

    @PostMapping(
        value = "/my/regions",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> updateMyRegion(
        @Valid @RequestBody UpdateMyRegionRequest request,
        @CurrentUser LoginUser loginUser
    ) {

        URI uri = LocationBuilder.buildLocation(memberRegionService.registerMyAddress(request.toServiceRequest(loginUser.memberId())));
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(
        value = "/my/regions/{addressId}"
    )
    public ResponseEntity<Void> deleteMyRegion(
        @PathVariable("addressId") Long addressId,
        @CurrentUser LoginUser loginUser
    ) {
        memberRegionService.deleteMyAddress(addressId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(
        value = "/my/current/regions",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindMyCurrentRegionResponse> findMyCurrentRegion(
        @ModelAttribute @Valid FindMyCurrentRegionRequest request
    ) {
        return ResponseEntity.ok(memberRegionService.findMyCurrentRegions(request.latitude(), request.longitude()));
    }
}
