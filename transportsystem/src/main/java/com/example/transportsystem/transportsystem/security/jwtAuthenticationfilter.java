	package com.example.transportsystem.transportsystem.security;
	
	import java.io.IOException;
	
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.Authentication;
	import org.springframework.security.core.context.SecurityContextHolder;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
	import org.springframework.stereotype.Service;
	import org.springframework.web.filter.OncePerRequestFilter;
	
	import com.example.transportsystem.transportsystem.service.Jwtservice;
	
	import io.jsonwebtoken.ExpiredJwtException;
	import io.jsonwebtoken.MalformedJwtException;
	import jakarta.servlet.FilterChain;
	import jakarta.servlet.ServletException;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	
	@Service 
	public class jwtAuthenticationfilter extends OncePerRequestFilter{
		
	    private Jwtservice jwtservice;
	    private UserDetailsService UserDetailsService;
	    
	    public jwtAuthenticationfilter(Jwtservice jwtservice,UserDetailsService UserDetailsService) {
	    	this.jwtservice=jwtservice;
	    	this.UserDetailsService=UserDetailsService;
	    }
	    
	    String userName = null;
	    String Token=null;
	   
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			
			final String authHeader=request.getHeader("Authorization");
			System.out.println("Authorization Header: " + authHeader);
			
			if(authHeader==null|| !authHeader.startsWith("Bearer")) {
			    System.out.println("Authorization header is missing or doesn't start with Bearer");
	
				filterChain.doFilter(request, response);
				return;
			}
			
				 Token=authHeader.substring(7);
				 System.out.println(Token);
				
				 try {
					    userName=jwtservice.extractUserName(Token);
					    System.out.print(userName);
		            } catch (IllegalArgumentException e) {
		                System.out.println("Unable to get JWT Token");
		                e.printStackTrace();
		            } catch (ExpiredJwtException e) {
		                System.out.println("JWT Token has expired");
		                e.printStackTrace();
		            }catch(MalformedJwtException e) {
		            	 System.out.println("Some changes has done in token");
		            }catch(Exception e) {
		            	e.printStackTrace();
		            }
		        
			    Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
			    
			 if(userName!=null && authentication==null) 
			 {
				 UserDetails userdetails= this.UserDetailsService.loadUserByUsername(userName);
			     System.out.println(userdetails);
			       
			       
				 if(jwtservice.isValidToken(Token,userdetails)) 
				 {
					         UsernamePasswordAuthenticationToken authenticationtoken=new UsernamePasswordAuthenticationToken(
							 userdetails,null,userdetails.getAuthorities()	
							 );
				         authenticationtoken.setDetails(
						 new WebAuthenticationDetailsSource()
						.buildDetails(request));
				         SecurityContextHolder.getContext().setAuthentication(authenticationtoken);
				 }else {
					 System.out.println("Validation fails");
				 }
				 
			 }
				       filterChain.doFilter(request, response);
			
		}
		}
	
	
