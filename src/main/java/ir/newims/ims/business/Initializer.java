package ir.newims.ims.business;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.management.element.ElementRepo;
import ir.newims.ims.business.management.element.TypeRepo;
import ir.newims.ims.business.management.element.dto.ElementDto;
import ir.newims.ims.business.management.element.dto.TypeDto;
import ir.newims.ims.business.personnel.leaverequest.LeaveRequestCode;
import ir.newims.ims.exception.BusinessException;
import ir.newims.ims.filter.filter.AccessFilter;
import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.management.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Component
public class Initializer {
    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    ElementRepo elementRepo;
    @Autowired
    TypeRepo typeRepo;

    private List<ElementDto> elements = new LinkedList<>();
    private List<TypeDto> types = new LinkedList<>();


    @PostConstruct
    private void init() {
        addElement();
        addTypes();

        persistType();
        persistElement();
    }


    private void addTypes() {
        types.add(new TypeDto(LeaveRequestCode.REQUEST_TYPE));
        types.add(new TypeDto(LeaveRequestCode.REQUEST_STATUS));
    }

    private void addElement() {
        elements.add(new ElementDto("استحقاقی", LeaveRequestCode.ENTITLEMENT_REQUEST_TYPE, LeaveRequestCode.REQUEST_TYPE));
        elements.add(new ElementDto("جهت اطلاع", LeaveRequestCode.FOR_INFORMATION_REQUEST_TYPE, LeaveRequestCode.REQUEST_TYPE));
        elements.add(new ElementDto("استعلاجی", LeaveRequestCode.ILLNESS_REQUEST_TYPE, LeaveRequestCode.REQUEST_TYPE));
        elements.add(new ElementDto("ثبت شده", LeaveRequestCode.REGISTERED_REQUEST_STATUS, LeaveRequestCode.REQUEST_STATUS));
        elements.add(new ElementDto("تایید شده", LeaveRequestCode.CONFIRMED_REQUEST_STATUS, LeaveRequestCode.REQUEST_STATUS));
        elements.add(new ElementDto("رد شده", LeaveRequestCode.REJECTED_REQUEST_STATUS, LeaveRequestCode.REQUEST_STATUS));
    }

    private void persistType() {
        for (TypeDto tp : types) {
            try {
                Optional<Type> typeOptional = typeRepo.findByCode(tp.getCode());
                if (!typeOptional.isPresent()) {
                    Type type = new Type();
                    type.setCode(tp.getCode());
                    typeRepo.save(type);
                    logger.info("TYPE PERSISTS: " + type.getCode());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }

    private void persistElement() {
        for (ElementDto elm : elements) {
            try {
                Optional<Element> elementOptional = elementRepo.findByCode(elm.getCode());
                if (!elementOptional.isPresent()) {
                    Type type = typeRepo.findByCode(elm.getTypeCode()).orElseThrow(() -> {
                        throw new BusinessException(
                                ResponseConstant.INVALID_FOOT_WORK_LOG_ID,
                                ResponseConstantMessage.INVALID_FOOT_WORK_LOG_ID);
                    });
                    Element element = new Element();
                    element.setType(type);
                    element.setName(elm.getName());
                    element.setCode(elm.getCode());
                    elementRepo.save(element);
                    logger.info("ELEMENT PERSISTS: " + element.getCode());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

}
