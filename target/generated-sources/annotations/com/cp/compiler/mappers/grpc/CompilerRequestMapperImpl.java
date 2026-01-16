package com.cp.compiler.mappers.grpc;

import com.cp.compiler.contract.CompilerProto;
import com.cp.compiler.contract.RemoteCodeCompilerRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-16T22:58:40+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Red Hat, Inc.)"
)
@Component
public class CompilerRequestMapperImpl implements CompilerRequestMapper {

    @Override
    public RemoteCodeCompilerRequest toCompilerRequest(CompilerProto.RemoteCodeCompilerRequest protoRequest) {
        if ( protoRequest == null ) {
            return null;
        }

        RemoteCodeCompilerRequest remoteCodeCompilerRequest = new RemoteCodeCompilerRequest();

        remoteCodeCompilerRequest.setTestCases( CompilerRequestMapper.toTestCases( protoRequest.getTestCases() ) );
        remoteCodeCompilerRequest.setLanguage( CompilerRequestMapper.toLanguage( protoRequest.getLanguage() ) );
        remoteCodeCompilerRequest.setSourcecode( protoRequest.getSourcecode() );
        remoteCodeCompilerRequest.setTimeLimit( protoRequest.getTimeLimit() );
        remoteCodeCompilerRequest.setMemoryLimit( protoRequest.getMemoryLimit() );

        return remoteCodeCompilerRequest;
    }
}
