import {useMutation, useQueryClient} from "@tanstack/react-query";
import {handleDelete, handlePost, handlePut} from "@/services/api.jsx";
import toast from "react-hot-toast";
import showErrorToast from "@/components/utils/ShowErrorToast.jsx";


export const useCreateMutation = (
    {
        successMessage = undefined,
        errorMessage = "Something went wrong!",
        queryKeysToInvalidate = [],
        mutationOptions = {}
    } = {}) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ url, data, options }) => handlePost(url, data, options),
        onSuccess: () => {
            !!successMessage && toast.success(successMessage);
        },
        onError: (error) => {
            showErrorToast(error, error ? error.response?.data?.error : errorMessage);
        },
        onSettled: async (_, error) => {
            if (!error && queryKeysToInvalidate.length > 0) {
                await Promise.all(
                    queryKeysToInvalidate.map((key) =>
                        queryClient.invalidateQueries({ queryKey: [key] })
                    )
                );
            }
        },
        ...mutationOptions,
    });
};
export const useDeleteMutation = (
    {
        successMessage = undefined,
        errorMessage = "Something went wrong!",
        queryKeysToInvalidate = [],
        mutationOptions = {}
    } = {}) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ url, options }) => handleDelete(url, options),
        onSuccess: (data, variables, context)  => {
            !!successMessage && toast.success(successMessage);
            mutationOptions.onSuccess?.(data, variables, context);
        },
        onError: (error) => {
            showErrorToast(error, error ? error.response?.data?.error : errorMessage);
        },
        onSettled: async (_, error) => {
            if (!error && queryKeysToInvalidate.length > 0) {
                await Promise.all(
                    queryKeysToInvalidate.map((key) =>
                        queryClient.invalidateQueries({ queryKey: [key] })
                    )
                );
            }
        },
        ...mutationOptions,
    });
};
export const useUpdateMutation = (
    {
        successMessage = "Operation successful!",
        errorMessage = "Something went wrong!",
        queryKeysToInvalidate = [],
        mutationOptions = {}
    } = {}) => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ url, data, options }) => handlePut(url, data, options),
        onSuccess: () => {
            toast.success(successMessage);
        },
        onError: (error) => {
            showErrorToast(error, error ? error.response?.data?.error : errorMessage);
        },
        onSettled: async (_, error, variables) => {
            if (!error && queryKeysToInvalidate.length > 0) {
                await Promise.all(
                    queryKeysToInvalidate.flatMap((key) => [
                        queryClient.invalidateQueries({ queryKey: [key] }),
                        queryClient.invalidateQueries({ queryKey: [key, variables?.data?.id] })
                    ])
                );
            }
        },
        ...mutationOptions,
    });
};
