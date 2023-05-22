package rikkei.academy.guitarplusclonejava.service;

import rikkei.academy.guitarplusclonejava.model.Feedback;

public interface IFeedbackService extends IGenericService<Feedback> {
    Feedback findFeedbackByOrderId(int orderId);
}
