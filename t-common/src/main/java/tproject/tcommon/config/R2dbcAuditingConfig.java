//package tproject.tcommon.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.ReactiveAuditorAware;
//
//@Configuration
//@EnableR2dbcAuditing
//public class R2dbcAuditingConfig {
//
//    @Bean
//    ReactiveAuditorAware<String> auditorAware() {
//        // Trả về người dùng hiện tại từ context bảo mật
//        return () -> ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getName)
//                .defaultIfEmpty("system");
//    }
//}
