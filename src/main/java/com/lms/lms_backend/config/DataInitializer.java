package com.lms.lms_backend.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.lms.lms_backend.entity.Answer;
import com.lms.lms_backend.entity.Category;
import com.lms.lms_backend.entity.ContentType;
import com.lms.lms_backend.entity.Course;
import com.lms.lms_backend.entity.CourseStatus;
import com.lms.lms_backend.entity.Lesson;
import com.lms.lms_backend.entity.Question;
import com.lms.lms_backend.entity.Quiz;
import com.lms.lms_backend.entity.Role;
import com.lms.lms_backend.entity.Section;
import com.lms.lms_backend.entity.User;
import com.lms.lms_backend.repository.CategoryRepository;
import com.lms.lms_backend.repository.CourseRepository;
import com.lms.lms_backend.repository.LessonRepository;
import com.lms.lms_backend.repository.QuizRepository;
import com.lms.lms_backend.repository.SectionRepository;
import com.lms.lms_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            log.info("Dữ liệu hệ thống đã tồn tại. Bỏ qua khởi tạo DataInitializer.");
            return;
        }

        log.info("Bắt đầu khởi tạo dữ liệu mẫu cho hệ thống MOOC LMS...");

        // 1. Tạo Users mẫu
        User admin = User.builder()
                .username("admin")
                .email("admin@lms.com")
                .password(passwordEncoder.encode("admin123"))
                .fullName("Quản trị viên Hệ thống")
                .avatarUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150")
                .role(Role.ROLE_ADMIN)
                .isActive(true)
                .build();

        User instructor = User.builder()
                .username("instructor")
                .email("instructor@lms.com")
                .password(passwordEncoder.encode("instructor123"))
                .fullName("TS. Nguyễn Văn A")
                .avatarUrl("https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150")
                .role(Role.ROLE_INSTRUCTOR)
                .isActive(true)
                .build();

        User student = User.builder()
                .username("student")
                .email("student@lms.com")
                .password(passwordEncoder.encode("student123"))
                .fullName("Trần Văn Học Viên")
                .avatarUrl("https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150")
                .role(Role.ROLE_STUDENT)
                .isActive(true)
                .build();

        userRepository.saveAll(List.of(admin, instructor, student));
        log.info("Đã tạo 3 tài khoản mẫu: admin@lms.com, instructor@lms.com, student@lms.com");

        // 2. Tạo Categories mẫu
        Category catWeb = Category.builder()
                .name("Lập trình Web")
                .slug("lap-trinh-web")
                .description("Các khóa học phát triển ứng dụng Web Frontend và Backend hiện đại")
                .build();

        Category catAI = Category.builder()
                .name("Trí tuệ nhân tạo & Data Science")
                .slug("ai-data-science")
                .description("Học máy, xử lý ngôn ngữ tự nhiên và phân tích dữ liệu lớn")
                .build();

        Category catMobile = Category.builder()
                .name("Lập trình Di động")
                .slug("lap-trinh-di-dong")
                .description("Xây dựng ứng dụng di động đa nền tảng React Native, Flutter")
                .build();

        categoryRepository.saveAll(List.of(catWeb, catAI, catMobile));
        log.info("Đã tạo 3 danh mục đào tạo mẫu.");

        // 3. Tạo Courses mẫu
        Course course1 = Course.builder()
                .title("Lập trình Web Fullstack với Spring Boot & ReactJS")
                .slug("fullstack-spring-boot-reactjs")
                .description("Khóa học toàn diện từ thiết kế CSDL MySQL, xây dựng RESTful API với Spring Boot 3-Layer Monolithic, đến phát triển Single Page Application giao diện hiện đại với ReactJS.")
                .thumbnailUrl("https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=600")
                .status(CourseStatus.PUBLISHED)
                .category(catWeb)
                .instructor(instructor)
                .build();

        Course course2 = Course.builder()
                .title("Khóa học Nhập môn Python & Machine Learning cơ bản")
                .slug("python-machine-learning-co-ban")
                .description("Trang bị kiến thức cốt lõi về ngôn ngữ Python, các thư viện NumPy, Pandas, Scikit-learn và các thuật toán học máy phổ biến.")
                .thumbnailUrl("https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=600")
                .status(CourseStatus.PUBLISHED)
                .category(catAI)
                .instructor(instructor)
                .build();

        Course course3 = Course.builder()
                .title("Xây dựng ứng dụng đa nền tảng với React Native")
                .slug("react-native-cross-platform")
                .description("Khóa học đang trong quá trình biên soạn và gửi phê duyệt lên Quản trị viên.")
                .thumbnailUrl("https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c?w=600")
                .status(CourseStatus.PENDING)
                .category(catMobile)
                .instructor(instructor)
                .build();

        courseRepository.saveAll(List.of(course1, course2, course3));
        log.info("Đã tạo 3 khóa học mẫu (2 Published, 1 Pending).");

        // 4. Tạo Sections & Lessons cho Course 1
        Section sec1 = Section.builder()
                .title("Chương 1: Tổng quan & Thiết lập môi trường")
                .orderIndex(1)
                .course(course1)
                .build();

        Section sec2 = Section.builder()
                .title("Chương 2: Phát triển Backend Spring Boot 3-Layer")
                .orderIndex(2)
                .course(course1)
                .build();

        Section sec3 = Section.builder()
                .title("Chương 3: Phát triển Frontend ReactJS & Course Player")
                .orderIndex(3)
                .course(course1)
                .build();

        sectionRepository.saveAll(List.of(sec1, sec2, sec3));

        Lesson les1 = Lesson.builder()
                .title("1.1 Giới thiệu kiến trúc hệ thống MOOC")
                .contentType(ContentType.VIDEO)
                .contentUrl("https://www.w3schools.com/html/mov_bbb.mp4")
                .durationMinutes(10)
                .orderIndex(1)
                .section(sec1)
                .build();

        Lesson les2 = Lesson.builder()
                .title("1.2 Hướng dẫn cài đặt Java 21, MySQL & Công cụ lập trình")
                .contentType(ContentType.DOCUMENT)
                .contentUrl("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf")
                .durationMinutes(15)
                .orderIndex(2)
                .section(sec1)
                .build();

        Lesson les3 = Lesson.builder()
                .title("2.1 Thiết kế 11 Bảng CSDL và các JPA Entity")
                .contentType(ContentType.VIDEO)
                .contentUrl("https://www.w3schools.com/html/mov_bbb.mp4")
                .durationMinutes(25)
                .orderIndex(1)
                .section(sec2)
                .build();

        Lesson les4 = Lesson.builder()
                .title("2.2 Hiện thực RESTful API & Bảo mật JWT Bearer")
                .contentType(ContentType.VIDEO)
                .contentUrl("https://www.w3schools.com/html/mov_bbb.mp4")
                .durationMinutes(30)
                .orderIndex(2)
                .section(sec2)
                .build();

        Lesson les5 = Lesson.builder()
                .title("3.1 Xây dựng Trình phát bài giảng Course Player & Tiến độ")
                .contentType(ContentType.VIDEO)
                .contentUrl("https://www.w3schools.com/html/mov_bbb.mp4")
                .durationMinutes(20)
                .orderIndex(1)
                .section(sec3)
                .build();

        lessonRepository.saveAll(List.of(les1, les2, les3, les4, les5));
        log.info("Đã tạo các chương và bài giảng đa phương tiện mẫu.");

        // 5. Tạo Quiz mẫu cho Course 1
        Quiz quiz = Quiz.builder()
                .course(course1)
                .title("Bài kiểm tra Đánh giá Năng lực Cuối khóa (Final Assessment)")
                .passingScore(80)
                .durationMinutes(15)
                .build();

        // Câu hỏi 1
        Question q1 = Question.builder()
                .quiz(quiz)
                .questionText("Kiến trúc Monolithic 3-Layer trong hệ thống bao gồm 3 tầng chính nào?")
                .point(1)
                .build();

        Answer a1_1 = Answer.builder().question(q1).answerText("Presentation (Controller), Business Logic (Service), Data Access (Repository)").isCorrect(true).build();
        Answer a1_2 = Answer.builder().question(q1).answerText("HTML, CSS, JavaScript").isCorrect(false).build();
        Answer a1_3 = Answer.builder().question(q1).answerText("Docker, Kubernetes, Nginx").isCorrect(false).build();
        Answer a1_4 = Answer.builder().question(q1).answerText("Model, View, Template").isCorrect(false).build();
        q1.setAnswers(List.of(a1_1, a1_2, a1_3, a1_4));

        // Câu hỏi 2
        Question q2 = Question.builder()
                .quiz(quiz)
                .questionText("Điều kiện để học viên được hệ thống tự động cấp Chứng chỉ số tốt nghiệp (Certificate) là gì?")
                .point(1)
                .build();

        Answer a2_1 = Answer.builder().question(q2).answerText("Chỉ cần bấm ghi danh vào khóa học").isCorrect(false).build();
        Answer a2_2 = Answer.builder().question(q2).answerText("Hoàn thành 100% các bài học và đạt điểm bài Quiz >= Passing Score").isCorrect(true).build();
        Answer a2_3 = Answer.builder().question(q2).answerText("Chỉ cần xem xong 1 video bài giảng").isCorrect(false).build();
        Answer a2_4 = Answer.builder().question(q2).answerText("Chờ quản trị viên duyệt thủ công").isCorrect(false).build();
        q2.setAnswers(List.of(a2_1, a2_2, a2_3, a2_4));

        // Câu hỏi 3
        Question q3 = Question.builder()
                .quiz(quiz)
                .questionText("Định dạng xác thực chuẩn của token JWT truyền trong HTTP Header là gì?")
                .point(1)
                .build();

        Answer a3_1 = Answer.builder().question(q3).answerText("Authorization: Bearer <token>").isCorrect(true).build();
        Answer a3_2 = Answer.builder().question(q3).answerText("Token: Basic <token>").isCorrect(false).build();
        Answer a3_3 = Answer.builder().question(q3).answerText("Cookie: SessionID=<token>").isCorrect(false).build();
        Answer a3_4 = Answer.builder().question(q3).answerText("Auth: API_KEY=<token>").isCorrect(false).build();
        q3.setAnswers(List.of(a3_1, a3_2, a3_3, a3_4));

        quiz.setQuestions(List.of(q1, q2, q3));
        quizRepository.save(quiz);
        log.info("Đã tạo bài Quiz kiểm tra đánh giá năng lực mẫu kèm câu hỏi & đáp án.");

        log.info("Khởi tạo dữ liệu mẫu hoàn tất thành công!");
    }
}
