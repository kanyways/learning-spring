package me.kany.project.learning.spring.ethereum.lib;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class Ustar extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060008054600160a060020a0319163317905560018055610574806100366000396000f3006080604052600436106100775763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416638da5cb5b811461007c578063adaa650b146100ad578063db5753ac1461014b578063e12688f414610172578063f2fde38b146101d2578063f3818c29146101f5575b600080fd5b34801561008857600080fd5b5061009161020d565b60408051600160a060020a039092168252519081900360200190f35b3480156100b957600080fd5b506100c560043561021c565b6040518084815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561010e5781810151838201526020016100f6565b50505050905090810190601f16801561013b5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561015757600080fd5b50610160610301565b60408051918252519081900360200190f35b34801561017e57600080fd5b50604080516020600460443581810135601f81018490048402850184019095528484526101609482359460248035953695946064949201919081908401838280828437509497506103249650505050505050565b3480156101de57600080fd5b506101f3600160a060020a03600435166103c8565b005b34801561020157600080fd5b506100c560043561040e565b600054600160a060020a031681565b600080548190606090600160a060020a0316331461023957600080fd5b600154841061024757600080fd5b600184101561025557600080fd5b6000848152600260208181526040928390208054600180830154928501805487519281161561010002600019011695909504601f810185900485028201850190965285815290975090955092908301828280156102f35780601f106102c8576101008083540402835291602001916102f3565b820191906000526020600020905b8154815290600101906020018083116102d657829003601f168201915b505050505090509193909250565b60008054600160a060020a0316331461031957600080fd5b506001546000190190565b60008054600160a060020a0316331461033c57600080fd5b6000848152600360205260409020541561035557600080fd5b50600180548082018255600085815260036020908152604080832084905580516060810182528881528083018881528183018881528686526002808652939095208251815590519681019690965592518051949593946103bd939285019291909101906104ad565b509050509392505050565b600054600160a060020a031633146103df57600080fd5b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600081815260036020526040812054600154909190606090831061043157600080fd5b600183101561043f57600080fd5b600083815260026020818152604092839020600180820154918401805486519281161561010002600019011694909404601f810184900484028201840190955284815290955092908301828280156102f35780601f106102c8576101008083540402835291602001916102f3565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106104ee57805160ff191683800117855561051b565b8280016001018555821561051b579182015b8281111561051b578251825591602001919060010190610500565b5061052792915061052b565b5090565b61054591905b808211156105275760008155600101610531565b905600a165627a7a72305820d227004fd89692b12da5165ea464a5b05bfc3a017f247556ca1d7fa04bf05a8c0029";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_GETCOMMODITYINFOBYINDEX = "getCommodityInfoByIndex";

    public static final String FUNC_GETCOMMODITYNUM = "getCommodityNum";

    public static final String FUNC_NEWCOMMODITY = "newCommodity";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_GETCOMMODITYINFOBYID = "getCommodityInfoById";

    @Deprecated
    protected Ustar(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Ustar(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Ustar(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Ustar(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple3<BigInteger, BigInteger, String>> getCommodityInfoByIndex(BigInteger commodityIndex) {
        final Function function = new Function(FUNC_GETCOMMODITYINFOBYINDEX,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(commodityIndex)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));

        return new RemoteCall<>(() -> {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple3<>(
                    (BigInteger) results.get(0).getValue(),
                    (BigInteger) results.get(1).getValue(),
                    (String) results.get(2).getValue());
        });

        /*return new RemoteCall<Tuple3<BigInteger, BigInteger, String>>(
                new Callable<Tuple3<BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, String>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (String) results.get(2).getValue());
                    }
                });*/

    }

    public RemoteCall<BigInteger> getCommodityNum() {
        final Function function = new Function(FUNC_GETCOMMODITYNUM,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> newCommodity(BigInteger commodityId, BigInteger seedBlock, String MD5) {
        final Function function = new Function(
                FUNC_NEWCOMMODITY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(commodityId),
                new org.web3j.abi.datatypes.generated.Uint256(seedBlock),
                new org.web3j.abi.datatypes.Utf8String(MD5)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<BigInteger, BigInteger, String>> getCommodityInfoById(BigInteger commodityId) {
        final Function function = new Function(FUNC_GETCOMMODITYINFOBYID,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(commodityId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));

        return new RemoteCall<>(() -> {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple3<>(
                    (BigInteger) results.get(0).getValue(),
                    (BigInteger) results.get(1).getValue(),
                    (String) results.get(2).getValue());
        });


//        return new RemoteCall<Tuple3<BigInteger, BigInteger, String>>(
//                new Callable<Tuple3<BigInteger, BigInteger, String>>() {
//                    @Override
//                    public Tuple3<BigInteger, BigInteger, String> call() throws Exception {
//                        List<Type> results = executeCallMultipleValueReturn(function);
//                        return new Tuple3<BigInteger, BigInteger, String>(
//                                (BigInteger) results.get(0).getValue(),
//                                (BigInteger) results.get(1).getValue(),
//                                (String) results.get(2).getValue());
//                    }
//                });
    }

    @Deprecated
    public static Ustar load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ustar(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Ustar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ustar(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Ustar load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Ustar(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Ustar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Ustar(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Ustar> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Ustar.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Ustar> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Ustar.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Ustar> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Ustar.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Ustar> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Ustar.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
